import request from '@/utils/request.js'

export const userRegisterService = (registerData) =>{
    const params = new URLSearchParams();
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/user/register',params);
}
export const userLoginService = (loginData) =>{
    let params = new URLSearchParams();
    for(let key in loginData){
        params.append(key,loginData[key])
    }
    return request.post('/user/login',params)
}
//获取个人信息
export const userInfoGetService = ()=>{
    return request.get('/user/userInfo');
}
export const userInforUpdateService= (userInfoData)=>{
    return request.put('/user/update',userInfoData)
}
export const userResetPasswordService = (PwdData)=>{
    return request.patch('/user/updatePwd',PwdData)
}