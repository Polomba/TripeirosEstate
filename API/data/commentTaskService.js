'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listComments = async (req, res) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            SELECT * FROM [dbo].[Coments];
        `;

        const result = await pool.request().query(query);

        return result.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};

const listCommentsByTaskId = async (taskId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            SELECT * FROM [dbo].[Coments] WHERE [TaskId] = @taskId;
        `;

        const result = await pool.request()
            .input('taskId', sql.Int, taskId)
            .query(query);

        return result.recordset;
    } catch (error) {
        return error.message;
    }
};


const createCommentTask = async (data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Coments] ' +
            '([Comment],[TaskId],[UserId]) ' +
            'VALUES (@Comment, @TaskId, @UserId) ';

        const insertConteudo = await pool.request()
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
        let query = 'UPDATE [dbo].[Coments] SET ';
        const inputParams = ['Comment'];
        for (const param of inputParams) {
            query += data[param] ? `${param} = @${param}, ` : '';
        }
        query = query.slice(0, -2);
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
    listComments,
    createCommentTask,
    updateCommentTask,
    deleteCommentTask,
    listCommentsByTaskId
}
