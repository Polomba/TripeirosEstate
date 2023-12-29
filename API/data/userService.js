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
        let query = 'UPDATE [dbo].[User] SET Roles = @Roles' +
            'WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Utilizador_Roles', sql.VarChar(255), Roles)
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


module.exports={
    listUtilizadores,
    listUtilizadorById,
    listUtilizadorByEmail,
    updateRolesUtilizador,
    updateEstadoUtilizador
}
