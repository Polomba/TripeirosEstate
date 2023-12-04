'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listPagamentos = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT [Id],[Value],[Estado],[EmissionDate],[Paymentdate],[Description],[Type],[UserId]' +
            'FROM [dbo].[Payment]';
        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const listUserPagamentos = async (UtilizadorId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT [Id],[Value],[Estado],[EmissionDate],[Paymentdate],[Description],[Type],[UserId]' +
            'FROM [dbo].[Payment] ' +
            'WHERE [UserId]=@UserId';
        const list = await pool.request()
            .input('UserId', sql.Int, UserId)
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const listPagamentoById = async (Id)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Value],[Estado],[EmissionDate],[Paymentdate],[Description],[Type],[UserId]' +
            'FROM [dbo].[Payment]' +
            'WHERE [Id] = @Id';

        const onePayment = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);

        return onePayment.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const listPagamentosPremium = async ()=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[ValorPagar],[Estado],[DataEmissao],[DataPagamento],[Descricao],[TipoPagamento]' +
            'FROM [dbo].[Pagamento]' +
            'WHERE [TipoPagamento] = @TipoPagamento';

        const pagamentosPremium = await pool.request()
            .input('TipoPagamento', sql.VarChar(255), "Tier premium")
            .query(query);

        return pagamentosPremium.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const listPagamentoPremiumNaoPago = async (userId)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[ValorPagar],[Estado],[DataEmissao],[DataPagamento],[Descricao],[TipoPagamento]' +
            'FROM [dbo].[Pagamento]' +
            'WHERE [TipoPagamento] = @TipoPagamento ' +
            'AND [Estado] = @Estado ' +
            'AND [UtilizadorId] = @UtilizadorId';

        const pagamentosPremium = await pool.request()
            .input('TipoPagamento', sql.VarChar(255), "Tier premium")
            .input('Estado', sql.VarChar(255), utils.estadosPagamentos.EP_NaoPago)
            .input('UtilizadorId', sql.Int, userId)
            .query(query);

        return pagamentosPremium.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const listTopPagamentoPremium = async (UtilizadorId)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT TOP (1) [Id],[ValorPagar],[Estado],[DataEmissao],[DataPagamento],[Descricao],[TipoPagamento]' +
            'FROM [dbo].[Pagamento] ' +
            'WHERE [TipoPagamento] = @TipoPagamento AND [UtilizadorId] = @UtilizadorId ' +
            'ORDER BY [DataEmissao] DESC';

        const pagamentosPremium = await pool.request()
            .input('TipoPagamento', sql.VarChar(255), "Tier premium")
            .input('UtilizadorId', sql.Int, UtilizadorId)
            .query(query);

        return pagamentosPremium.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const createPagamentoPedido = async (PedidoId,ValorPagar,UtilizadorId) => {
    try {
        const currentDate = new Date();
        const sqlCurrentDateString = currentDate.toISOString().slice(0, 19).replace('T', ' ');

        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Pagamento] ' +
            '([ValorPagar],[Estado],[Descricao],[DataEmissao],[TipoPagamento],[PedidoId],[UtilizadorId]) ' +
            'VALUES (@ValorPagar,@Estado,@Descricao,@DataEmissao,@TipoPagamento,@PedidoId,@UtilizadorId) ' +
            'SELECT SCOPE_IDENTITY() AS Id';

        const insertPagamento = await pool.request()
            .input('ValorPagar', sql.Real, ValorPagar)
            .input('Estado', sql.VarChar(255), utils.estadosPagamentos.EP_NaoPago)
            .input('Descricao', sql.VarChar(255), `Pagamento do pedido Nº${PedidoId}`)
            .input('DataEmissao', sql.DateTime, sqlCurrentDateString)
            .input('TipoPagamento', sql.VarChar(255), "Pedido")
            .input('PedidoId', sql.Int, PedidoId)
            .input('UtilizadorId', sql.Int, UtilizadorId)
            .query(query);

        return insertPagamento.recordset;
    }
    catch (error) {
        return error.message;
    }
}
const createPagamentoPremium = async (UtilizadorId) => {
    try {
        const currentDate = new Date();
        const sqlCurrentDateString = currentDate.toISOString().slice(0, 19).replace('T', ' ');

        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Pagamento] ' +
            '([ValorPagar],[Estado],[Descricao],[DataEmissao],[TipoPagamento],[UtilizadorId]) ' +
            'VALUES (@ValorPagar,@Estado,@Descricao,@DataEmissao,@TipoPagamento,@UtilizadorId) ' +
            'SELECT SCOPE_IDENTITY() AS Id';

        const insertPagamento = await pool.request()
            .input('ValorPagar', sql.Real, 7.99)
            .input('Estado', sql.VarChar(255), utils.estadosPagamentos.EP_NaoPago)
            .input('Descricao', sql.VarChar(255), `Pagamento referente ao mês ${currentDate.getMonth() + 1}`)
            .input('DataEmissao', sql.DateTime, sqlCurrentDateString)
            .input('TipoPagamento', sql.VarChar(255), "Tier premium")
            .input('UtilizadorId', sql.Int, UtilizadorId)
            .query(query);

        return insertPagamento.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const updatePagamento = async (Id, pagamentoData) => {
    try {
        const currentDate = new Date();
        const sqlCurrentDateString = currentDate.toISOString().slice(0, 19).replace('T', ' ');

        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Pagamento] SET Estado=@Estado, DataPagamento=@DataPagamento WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Estado', sql.VarChar(255), pagamentoData.Estado)
            .input('DataPagamento', sql.DateTime, sqlCurrentDateString)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const updateTerminarPrazoPagamento = async (Id) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Pagamento] SET Estado=@Estado WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Estado', sql.VarChar(255), utils.estadosPagamentos.EP_TerminouPrazo)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listPagamentos,
    listUserPagamentos,
    listPagamentoById,
    listPagamentosPremium,
    listPagamentoPremiumNaoPago,
    listTopPagamentoPremium,
    createPagamentoPedido,
    createPagamentoPremium,
    updatePagamento,
    updateTerminarPrazoPagamento
}