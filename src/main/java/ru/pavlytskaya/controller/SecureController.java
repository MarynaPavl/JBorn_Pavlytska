package ru.pavlytskaya.controller;

public interface SecureController<REQ, RES> {
    RES handler(REQ request, Long userId);

    Class<REQ> getRequestClass();
}
