package com.example.bankapp.config;

import com.example.bankapp.services.telegram.BankTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig {

    private final BankTelegramBot bankTelegramBot;

    @Value("${telegram.bot.token}")
    private String botToken;

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bankTelegramBot);
            log.info("🤖 Bot Telegram enregistré avec succès !");
        } catch (TelegramApiException e) {
            log.error("❌ Erreur lors de l'enregistrement du bot Telegram", e);
        }
    }
}