'use strict'
const express = require('express');
const pagamentoController = require('../controllers/pagamentoController');
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const premiumTier = require("../middleware/premiumTier");
const pagamentoPedidos = require("../middleware/pagamentoPedido")
const router = express.Router();

const {getPagamentos, getUserPagamentos, getPagamento, getPagamentosPremium, getPagamentoPremiumNaoPago, getTopPagamentoPremium,
    addPagamentoPremium, updatePagamento } = pagamentoController;

router.get('/Pagamentos', authCookie.authCookieVerify, getPagamentos);
router.get('/UserPagamentos/:userId', authCookie.authCookieVerify, premiumTier.pagamentoPremiumVerify, pagamentoPedidos.pagamentoPedidosVerify, getUserPagamentos);
router.get('/Pagamento/:Id', authCookie.authCookieVerify, getPagamento);
router.get('/PagamentoPremiumNaoPago/:userId', authCookie.authCookieVerify, getPagamentoPremiumNaoPago);
router.get('/PagamentosPremium', authCookie.authCookieVerify, getPagamentosPremium);
router.get('/TopPagamentoPremium', authCookie.authCookieVerify, getTopPagamentoPremium);

router.post('/PagamentoPremium', authCookie.authCookieVerify, addPagamentoPremium);

router.put('/Pagamento/:Id', authCookie.authCookieVerify, updatePagamento);

module.exports = {
    routes: router
}