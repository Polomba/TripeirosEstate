'use strict'
const express = require('express');
const cookieParser = require('cookie-parser');
const cors = require('cors');
const bodyParser = require('body-parser');
const app = express();

app.use(cors());
app.use(bodyParser.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());

/*
app.use('/api', require('./routes/conteudoRoutes').routes);
app.use('/api', require('./routes/utlizadorRoutes').routes);
app.use('/api', require('./routes/produtoRoutes').routes);
app.use('/api', require('./routes/menuRoutes').routes);
app.use('/api', require('./routes/estafetaRoutes').routes);
app.use('/api', require('./routes/bibliotecaRoutes').routes);
app.use('/api', require('./routes/atorRoutes').routes);
app.use('/api', require('./routes/authRoutes').routes);
app.use('/api', require('./routes/pagamentoRoutes').routes);
app.use('/api', require('./routes/pedidoRoutes').routes);
app.use('/api', require('./routes/reviewsPremiumRoutes').routes);
app.use('/api', require('./routes/generoRoutes').routes);
app.use('/api', require('./routes/alteracaoConteudoRoutes').routes);
app.use('/api', require('./routes/estatisticasRoutes').routes);
*/

module.exports = app;