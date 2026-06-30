package com.example.bankapp.services.telegram;

import com.example.bankapp.services.ai.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final RagService ragService;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        // Le token est injecté automatiquement par telegrambots-spring-boot-starter
        // via la propriété telegram.bot.token
        return System.getenv("TELEGRAM_BOT_TOKEN"); 
        // Ou utilise @Value("${telegram.bot.token}") et stocke-le dans un champ
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getFirstName();

            log.info("💬 Message reçu de {} : {}", username, messageText);

            // Commande /start
            if (messageText.equals("/start")) {
                sendMessage(chatId, 
                    "👋 Bonjour " + username + " !\n\n" +
                    "Je suis l'assistant virtuel de Bank App.\n\n" +
                    "Pose-moi tes questions sur :\n" +
                    "• Les frais bancaires\n" +
                    "• Les plafonds de carte\n" +
                    "• Les taux d'intérêt\n" +
                    "• Les conditions générales\n\n" +
                    "Exemple : 'Quels sont les frais de retrait à l'étranger ?'"
                );
                return;
            }

            // Indicateur de frappe (typing...)
            sendChatAction(chatId);

            try {
                // Appel au service RAG
                String response = ragService.askQuestion(messageText);
                sendMessage(chatId, response);
            } catch (Exception e) {
                log.error("❌ Erreur lors du traitement de la question", e);
                sendMessage(chatId, "⚠️ Désolé, une erreur s'est produite. Veuillez réessayer plus tard.");
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        
        try {
            execute(message);
            log.info("✅ Réponse envoyée à chatId {}", chatId);
        } catch (TelegramApiException e) {
            log.error("❌ Erreur lors de l'envoi du message", e);
        }
    }

    private void sendChatAction(long chatId) {
        try {
            org.telegram.telegrambots.meta.api.methods.send.SendChatAction action = 
                new org.telegram.telegrambots.meta.api.methods.send.SendChatAction();
            action.setChatId(String.valueOf(chatId));
            action.setAction("typing");
            execute(action);
        } catch (TelegramApiException e) {
            log.error("Erreur lors de l'envoi de l'action de chat", e);
        }
    }
}