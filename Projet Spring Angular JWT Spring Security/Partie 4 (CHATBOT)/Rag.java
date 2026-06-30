package com.example.bankapp.services.ai;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:documents/conditions-bancaires.txt")
    private Resource documentResource;

    @PostConstruct
    public void init() {
        log.info("📚 Indexation du document de conditions bancaires...");
        
        // Lecture du document
        TextReader textReader = new TextReader(documentResource);
        List<Document> documents = textReader.get();

        // Split du document en chunks plus petits
        TokenTextSplitter textSplitter = new TokenTextSplitter();
        List<Document> splitDocuments = textSplitter.apply(documents);

        // Indexation dans le VectorStore
        vectorStore.add(splitDocuments);
        
        log.info("✅ {} chunks indexés dans le VectorStore", splitDocuments.size());
    }

    public String askQuestion(String question) {
        log.info("🤖 Question reçue : {}", question);

        // Recherche des documents pertinents (similitude sémantique)
        List<Document> relevantDocs = vectorStore.similaritySearch(
            SearchRequest.query(question).withTopK(3)
        );

        if (relevantDocs.isEmpty()) {
            return "Désolé, je n'ai pas trouvé d'informations pertinentes dans nos conditions bancaires.";
        }

        // Construction du contexte à partir des documents trouvés
        String context = relevantDocs.stream()
            .map(Document::getContent)
            .collect(Collectors.joining("\n\n"));

        log.info("📄 Contexte RAG trouvé ({} documents)", relevantDocs.size());

        // Création du prompt avec le contexte
        PromptTemplate promptTemplate = new PromptTemplate("""
            Tu es un assistant bancaire virtuel pour Bank App.
            
            Réponds à la question de l'utilisateur en utilisant UNIQUEMENT les informations du contexte ci-dessous.
            Si la réponse n'est pas dans le contexte, dis poliment que tu ne sais pas et suggère de contacter le service client.
            
            CONTEXTE :
            {context}
            
            QUESTION : {question}
            
            RÉPONSE (en français, claire et concise) :
            """);

        Prompt prompt = promptTemplate.create(Map.of(
            "context", context,
            "question", question
        ));

        // Appel à OpenAI
        String response = chatClient.call(prompt).getContent();
        
        log.info("✅ Réponse générée : {}", response);
        return response;
    }
}