'use strict'
const express = require('express');
const pagamentoController = require('../controllers/pagamentoController');
const authCookie = require("../middleware/authCookieVerify");
const premiumTier = require("../middleware/premiumTier");
const pagamentoPedidos = require("../middleware/pagamentoPedido")
const router = express.Router();

const {getPagamentos, getUserPagamentos, getPagamento, getPagamentosPremium, getPagamentoPremiumNaoPago, getTopPagamentoPremium,
    addPagamentoPremium, updatePagamento } = pagamentoController;

router.get('/Pagamentos',  getPagamentos);
router.get('/UserPagamentos/:userId',  premiumTier.pagamentoPremiumVerify, pagamentoPedidos.pagamentoPedidosVerify, getUserPagamentos);
router.get('/Pagamento/:Id',  getPagamento);
router.get('/PagamentoPremiumNaoPago/:userId',  getPagamentoPremiumNaoPago);
router.get('/PagamentosPremium', getPagamentosPremium);
router.get('/TopPagamentoPremium', getTopPagamentoPremium);

router.post('/PagamentoPremium', addPagamentoPremium);

router.put('/Pagamento/:Id', updatePagamento);

module.exports = {
    routes: router
}