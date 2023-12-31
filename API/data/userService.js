'use strict';
const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listUtilizadores = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query =  'SELECT [Id],[Name],[Email]' +
            '[ProfilePicture],[Roles],[Token]' +
            'FROM [dbo].[User]';
        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const listUtilizadorById = async (Id)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[Password]' +
            ',[Roles],[Token]' +
            'FROM [dbo].[User]' +
            'WHERE [Id] = @Id';

        const oneUtilizador = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);

        return oneUtilizador.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const listUtilizadorByEmail = async (Email)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[Roles],[Token],[ProfilePicture]' +
            'FROM [dbo].[User]' +
            'WHERE [Email] = @Email';

        const oneUtilizador = await pool.request()
            .input('Email', sql.VarChar(255), Email)
            .query(query);

        return oneUtilizador.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const updateRolesUtilizador = async (Id, Roles) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[User] SET Roles = @Roles ' +
            'WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Roles', sql.VarChar(255), Roles)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const createUtilizador = async (data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `INSERT INTO [dbo].[User] 
            ([Name], [Email], [Password], [Roles], [Token], [ProfilePicture]
            VALUES (@Name, @Email, @Password, @Roles, @Token, @ProfilePicture);
            SELECT SCOPE_IDENTITY() AS Id;
        `;

        const insertConteudo = await pool.request()
            .input('Name', sql.VarChar(255), data.Name)
            .input('Email', sql.VarChar(255), data.Email)
            .input('Password', sql.VarChar(255), data.Password)
            .input('Roles', sql.VarChar(255), data.Roles)
            .input('Token', sql.VarChar(255), data.Token)
            .input('ProfilePicture', sql.VarChar(255), data.ProfilePicture)
            .query(query);

        return insertConteudo.recordset;
    } catch (error) {
        return error.message;
    }
};

const deleteUtilizador = async (Id) => {
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

module.exports={
    listUtilizadores,
    listUtilizadorById,
    listUtilizadorByEmail,
    updateRolesUtilizador,
    createUtilizador,
    deleteUtilizador
}
