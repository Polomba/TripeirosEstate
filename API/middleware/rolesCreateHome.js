'use strict'
const jwt = require('jsonwebtoken');
const userData = require('../data/userService');
const houseData = require('../data/houseService')
const utils = require('../utils/utils');
const authData = require('../controllers/authController')

const checkRoleCreateHome = async (req, res, next) => {
    try {
        let token;
        let authHeader = req.headers['authorization'];

        if (!authHeader)
            return res.status(401).json({ message: 'Token not provided' });

        if (authHeader.indexOf(' ') >= 0)
            token = authHeader.split(' ')[1];
        else
            token = authHeader

        const decodedToken = jwt. verify(token, process.env.SECRET_TOKEN);
        console.log(decodedToken);
        const userId = decodedToken.user[0].Id;
        console.log(userId);

        const findUser = await userData.listUtilizadorById(userId);

        if (findUser[0].Roles === utils.user_roles.UR_Premium) {
            return next();
        } else {
                return res.status(401).json({
                    error: `Não pode criar casa (${userId}). Torne-se utilziador premium.`,
                });
            }
    } catch (error) {
        return res.status(401).json({ error: `${error.message}` });
    }
};

module.exports = {
    checkRoleCreateHome
}