package ru.mboychook.webQuestions.services;

public interface SecurityService {
        String findLoggedInUser();

        void autoLogin(String username, String password);
}
