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

const listUtilizadorByEmail = async (Email)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[ProfilePicture],[Roles],[Token]' +
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

const listUtilizadorByEmailPassword = async (Email)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[Password],[ProfilePicture],[Roles],[Token]' +
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
const updateRolesUtilizador = async (Id, Utilizador_Roles) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[User] SET Utilizador_Roles = @Utilizador_Roles ' +
            'WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Utilizador_Roles', sql.VarChar(255), Utilizador_Roles)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}
const updateEstadoUtilizador = async (Id, Roles) => {
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




const listUtilizadorById = async (Id) => {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email]' +
            '[ProfilePicture],[Roles],[Token]' +
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

const createNewRegisterUtilizador = async (userData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[User] ' +
            '([Name],[Email],[Password],[Roles],[Token]) ' +
            'VALUES (@Nome, @Email, @Password, @Roles, @Token) ';

        const insertUtilizador = await pool.request()
            .input('Nome', sql.VarChar(255), userData.Name)
            .input('Email', sql.VarChar(255), userData.Email)
            .input('Password', sql.VarChar(255), userData.Password)
            .input('Roles', sql.VarChar(255), utils.user_roles.UR_Normal)
            .input('Token', sql.VarChar(255), userData.Token)
            .query(query);

        return insertUtilizador.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const createUtilizador = async (userData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO [dbo].[User] ' +
            '([Name],[Email],[Password],[Roles],[Token], [ProfilePicture]) ' +
            'VALUES (@Nome, @Email, @Password, @Roles, @Token, @ProfilePicture)';

        const insertUtilizador = await pool.request()
            .input('Nome', sql.VarChar(255), userData.Nome)
            .input('Email', sql.VarChar(255), userData.Email)
            .input('Password', sql.VarChar(255), userData.Password)
            .input('Roles', sql.Int, userData.Roles)
            .input('Token', sql.VarChar(255), userData.Token)
            .input('ProfilePicture',sql.VarChar(255), userData.ProfilePicture)
            .query(query);

        return insertUtilizador.recordset;
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listUtilizadores,
    listUtilizadorByEmail,
    createUtilizador,
    createNewRegisterUtilizador,
    listUtilizadorById,
    listUtilizadorByEmailPassword
}