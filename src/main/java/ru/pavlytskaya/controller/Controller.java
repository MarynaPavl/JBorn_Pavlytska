package ru.pavlytskaya.controller;

public interface Controller<REQ, RES> {
    RES handler(REQ request);

    Class<REQ> getRequestClass();
}
