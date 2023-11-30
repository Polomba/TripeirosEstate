'use strict'

const jwt = require('jsonwebtoken');
const utilizadoresData = require('../data/userService');
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
        const token = jwt.sign({user}, process.env.SECRET_TOKEN, { expiresIn: "24h"});

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
        const userData = req.body;
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

        const newUserdata = req.body;
        await utilizadoresData.createNewRegisterUtilizador(userData);
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