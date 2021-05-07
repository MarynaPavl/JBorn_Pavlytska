//package ru.pavlytskaya.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.pavlytskaya.dao.TransactionToCategoryModel;
//import ru.pavlytskaya.json.TransactToCatygoryRequest;
//import ru.pavlytskaya.json.TransactToCatygoryResponse;
//import ru.pavlytskaya.service.TransactionToCategoryDTO;
//import ru.pavlytskaya.service.TransactionToCategoryService;
//
//@Service("/transactToCatygory")
//@RequiredArgsConstructor
//public class TransactToCatygoryController implements SecureController<TransactToCatygoryRequest, TransactToCatygoryResponse> {
//    private final TransactionToCategoryService toCategoryService;
//
//    @Override
//    public TransactToCatygoryResponse handler(TransactToCatygoryRequest request, Long userId) {
//        TransactionToCategoryDTO toCategoryDTO = toCategoryService.transactionToCategoryInsert(
//                request.getIdTransaction(), request.getIdCategory());
//        if (toCategoryDTO != null) {
//            return new TransactToCatygoryResponse("Operation was successfully completed.");
//        }
//        return null;
//    }
//
//    @Override
//    public Class<TransactToCatygoryRequest> getRequestClass() {
//        return TransactToCatygoryRequest.class;
//    }
//}
