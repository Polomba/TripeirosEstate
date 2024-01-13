'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listTarefas = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT [Id],[Title],[Description],[Data],[State],[Photo], [homeid], [UserId]' +
            'FROM [dbo].[Task]';
        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const listTarefaById = async (Id)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Title],[Description],[Data],[State],[Photo], [homeid], [UserId]'+
            'FROM [dbo].[Task]' +
            'WHERE [homeid] = @Id';

        const oneConteudo = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);

        return oneConteudo.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const listTarefaByTaskId = async (Id)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Title],[Description],[Data],[State],[Photo], [homeid], [UserId]'+
            'FROM [dbo].[Task]' +
            'WHERE [Id] = @Id';

        const oneConteudo = await pool.request()
            .query(query);

        return oneConteudo.recordset;
    }
    catch (error) {
        return  error.message;
    }
}


const listTarefaByTitle = async (Title)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Title],[Description],[Data]' +
            'FROM [dbo].[Task]' +
            'WHERE [Title] = @Title';

        const oneTarefa = await pool.request()
            .input('Title', sql.VarChar(255), Title)
            .query(query);

        return oneTarefa.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const createTarefa = async (tarefaData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `INSERT INTO [dbo].[Task] 
            ([Title], [Description], [Data], [State], [Photo], [Homeid], [UserId]) 
            VALUES (@Title, @Description, @Data, @State, @Photo, @Homeid, @UserId);
            SELECT SCOPE_IDENTITY() AS Id;
        `;

        const insertConteudo = await pool.request()
            .input('Title', sql.VarChar(255), tarefaData.Title)
            .input('Description', sql.VarChar(255), tarefaData.Description)
            .input('Data', sql.Date, tarefaData.Data)
            .input('State', sql.VarChar(255), tarefaData.State)
            .input('Photo', sql.VarChar(255), tarefaData.Photo)
            .input('Homeid', sql.Int, tarefaData.Homeid)
            .input('UserId', sql.Int, tarefaData.UserId)
            .query(query);

        return insertConteudo.recordset;
    } catch (error) {
        return error.message;
    }
};


const updateTarefa = async (Id, tarefaData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Task] SET ';
        const inputParams = ['Title', 'Description', 'Data', 'State', 'Photo', 'Homeid', 'UserId']; // Adicione os campos faltantes aqui
        for (const param of inputParams) {
            query += tarefaData[param] ? `${param} = @${param}, ` : '';
        }
        query = query.slice(0, -2);
        query +=` WHERE [Id]=@Id`;

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Title', sql.VarChar(255), tarefaData.Title)
            .input('Description', sql.VarChar(255), tarefaData.Description)
            .input('Data', sql.DateTime, tarefaData.Data)
            .input('State', sql.VarChar(255), tarefaData.State)
            .input('Photo', sql.VarChar(255), tarefaData.Photo)
            .input('Homeid', sql.Int, tarefaData.Homeid)
            .input('UserId', sql.Int, tarefaData.UserId)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
};

const deleteTarefa = async (Id) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE [dbo].[Task] WHERE [Id]=@Id;'

        const deleted = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);
        return deleted.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const countTasksCreatedByUserInCurrentWeek = async (userId) => {
    try {
        const now = new Date();
        const startOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay());
        const endOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay() + 6);

        let pool = await sql.connect(config.sql);
        let query = `
            SELECT COUNT(*) AS TaskCount 
            FROM [dbo].[Task] 
            WHERE [UserId] = @userId 
            AND [Data] BETWEEN @startOfWeek AND @endOfWeek
        `;

        const result = await pool.request()
            .input('userId', sql.Int, userId)
            .input('startOfWeek', sql.Date, startOfWeek)
            .input('endOfWeek', sql.Date, endOfWeek)
            .query(query);

        return result.recordset[0].TaskCount;
    } catch (error) {
        return error.message;
    }
};

module.exports = {
    listTarefas,
    listTarefaById,
    listTarefaByTitle,
    createTarefa,
    updateTarefa,
    deleteTarefa,
    countTasksCreatedByUserInCurrentWeek,
    listTarefaByTaskId
}