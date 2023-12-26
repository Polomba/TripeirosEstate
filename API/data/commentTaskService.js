'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listCommentsTasks = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT [Id],[Comment],[TaskId],[UserId] ' +
            'FROM [dbo].[Coments]' +
            'JOIN [dbo].[Task] ON TaskId = Comment.TaskId';
            'JOIN [dbo].[User] ON UserId = Comment.UserId';

        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const createCommentTask = async (Id, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Coments] ' +
            '([Id],[Comment],[TaskId],[UserId]) ' +
            'VALUES (@Id, @Comment, @TaskId, @UserId) ';

        const insertConteudo = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Comment', sql.VarChar(255), data.Comment)
            .input('TaskId', sql.Int, data.TaskId)
            .input('UserId', sql.Int, data.UserId)
            .query(query);

        return insertConteudo.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const updateCommentTask = async (uId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        /**
         * Atualiza os campos "Comment" da tabela "Coments".
         */
        let query = 'UPDATE [dbo].[Coments] SET ';
        const inputParams = ['Comment'];
        for (const param of inputParams) {
            query += data[param] ? `${param} = @${param}, ` : '';
        }
        query = query.slice(0, -2); // remove trailing comma and space
        query +=` WHERE [UserId]=@uId AND [TaskId]=@tId`

        const update = await pool.request()
            .input('uId', sql.Int, uId)
            .input('tId', sql.Int, data.tId)
            .input('Comment', sql.VarChar(255), data.Comment)
            .query(query);
        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const deleteCommentTask = async (uId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE [dbo].[Coments] WHERE [UserId]=@uId AND [TaskId]=@tId';

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
    listCommentsTasks,
    createCommentTask,
    updateCommentTask,
    deleteCommentTask,
}
