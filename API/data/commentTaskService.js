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
        const { Comment, TaskId, UserId } = data;
        const taskExists = await checkTaskExists(TaskId);

        if (!taskExists) {
            throw new Error('Tarefa não encontrada. Não é possível adicionar um comentário para uma tarefa inexistente.');
        }

        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[Coments] ' +
            '([Comment],[TaskId],[UserId]) ' +
            'VALUES (@Comment, @TaskId, @UserId) ';

        const insertConteudo = await pool.request()
            .input('Comment', sql.VarChar(255), Comment)
            .input('TaskId', sql.Int, TaskId)
            .input('UserId', sql.Int, UserId)
            .query(query);

        return insertConteudo.recordset;
    }
    catch (error) {
        return error.message;
    }
}


const checkTaskExists = async (taskId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT COUNT(*) AS Count FROM [dbo].[Task] WHERE [Id] = @taskId';

        const result = await pool.request()
            .input('taskId', sql.Int, taskId)
            .query(query);

        return result.recordset[0].Count > 0;
    } catch (error) {
        throw new Error('Erro ao verificar se a tarefa existe.');
    }
}


const updateCommentTask = async (commentId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Coments] SET ';

        const inputParams = ['Comment', 'TaskId', 'UserId'];

        for (const param of inputParams) {
            query += data[param] ? `${param} = @${param}, ` : '';
        }

        query = query.slice(0, -2);

        query += ' WHERE [Id] = @commentId;';

        const update = await pool.request()
            .input('commentId', sql.Int, commentId)
            .input('Comment', sql.VarChar(255), data.Comment)
            .input('TaskId', sql.Int, data.TaskId)
            .input('UserId', sql.Int, data.UserId)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const deleteCommentTask = async (commentId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE [dbo].[Coments] WHERE [Id] = @commentId;';

        const deleted = await pool.request()
            .input('commentId', sql.Int, commentId)
            .query(query);

        return deleted.recordset;
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
