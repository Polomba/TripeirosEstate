'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listReviewsTasks = async () => {
    try {
        let pool = await sql.connect(config.sql);
            let query = 'SELECT [TaskId],[Id],[Rating],[Comment] ' +
            'FROM [dbo].[Review]' +
            'JOIN [dbo].[Task] ON Task.Id = Review.TaskId';

        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const createReviewTask = async (Id, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Review] ' +
            '([Id],[TaskId],[Comment],[Rating]) ' +
            'VALUES (@UserId, @TaskId, @Comment, @Rating) ';

        const insertConteudo = await pool.request()
            .input('UserId', sql.Int, Id)
            .input('TaskId', sql.Int, data.TaskId)
            .input('Comment', sql.VarChar(255), data.Comment)
            .input('Rating', sql.Float, data.Rating)
            .query(query);

        return insertConteudo.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const updateReviewTask = async (uId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        /**
         * Atualiza os campos "Comment" e "Rating" da tabela "Review".
         */
        let query = 'UPDATE [dbo].[Review] SET ';
        const inputParams = ['Comment', 'Rating'];
        /**
         * Este código constrói uma consulta SQL dinâmica com base em um conjunto de parâmetros de entrada.
         * A consulta é usada para atualizar registros em um banco de dados.
         *
         * @param {Array} inputParams - Lista de parâmetros de entrada.
         * @param {Object} data - Dados fornecidos para construir a consulta.
         * @param {string} uId - Valor da variável "uId".
         * @param {string} tId - Valor da variável "tId".
         * @returns {string} - Consulta SQL final.
         */
        for (const param of inputParams) {
            query += data[param] ? `${param} = @${param}, ` : '';
        }
        query = query.slice(0, -2); // remove trailing comma and space
        query +=` WHERE [Id]=@uId AND [TaskId]=@tId`

        const update = await pool.request()
            .input('uId', sql.Int, uId)
            .input('tId', sql.Int, data.tId)
            .input('Review', sql.VarChar(255), data.Comment)
            .input('Rating', sql.Real, data.Rating)
            .query(query);
        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const deleteReviewTask = async (uId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE [dbo].[Review] WHERE [Id]=@uId AND [TaskId]=@tId';

        const update = await pool.request()
            .input('uId', sql.Int, uId)
            .input('tId', sql.Int, data.tId)
            .query(query);
        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listReviewsTasks,
    createReviewTask,
    updateReviewTask,
    deleteReviewTask,
}
