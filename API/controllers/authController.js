'use strict'

const jwt = require('jsonwebtoken');
const utilizadoresData = require('../data/utilizadorService');
const utils = require('../utils/utils');

const authUtilizador = async (req, res, next)=> {
    try {
        const { Email, Password } = req.body;
        const user = await utilizadoresData.listUtilizadorByEmail(Email);

        if (!user || !user.length || user[0].Password != Password) {
            return res.status(403).json({
                error: "Login invalido!"
            });
        }

        delete user[0].Password;
        const token = jwt.sign({user}, process.env.SECRET_TOKEN, { expiresIn: "1h"});

        res.cookie("token", token,{
            httpOnly: false,
        })

        return res.status(200).json({Authorization: `${token}`});
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const registerUtilizador = async (req, res)=> {
    try {
        const Email = req.body.Email;
        const Password = req.body.Password;
        const confirm_password = req.body.confirm_password;
        const userCheck = await utilizadoresData.listUtilizadorByEmail(Email);

        if (Object.keys(userCheck).length > 0) {
            if (userCheck[0].Email == Email) {
                return res.status(409).json({
                    error: "Email já em uso!",
                });
            }

            if (userCheck[0].Password) {
                delete userCheck[0].Password;
            }
        }


        if (Password !== confirm_password) {
            return res.status(409).json({
                error: "Password não é igual ao campo Confirm Password!"
            });
        }

        const newUserdata = req.body;
        await utilizadoresData.createNewRegisterUtilizador(newUserdata);
        const user = await utilizadoresData.listUtilizadorByEmail(Email);
        const token = jwt.sign({user}, process.env.SECRET_TOKEN, { expiresIn: "1h"});

        res.cookie("token", token,{
            httpOnly: false,
        })

        return res.status(200).json({Authorization: `${token}`});
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    authUtilizador,
    registerUtilizador
}