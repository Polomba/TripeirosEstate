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

app.use('/api', require('./routes/authRoutes').routes);
app.use('/api', require('./routes/commentTaskRoute').routes);
app.use('/api', require('./routes/pagamentoRoutes').routes);
app.use('/api', require('./routes/reviewsTaskRoutes').routes);
app.use('/api', require('./routes/tarefaRoutes').routes);

module.exports = app;