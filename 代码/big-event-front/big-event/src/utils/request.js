//定制请求的实例
import { ElMessage } from 'element-plus'
//导入axios  npm install axios
import axios from 'axios';
//在js中这样使用
import router from '@/router'
import { useTokenStore } from '@/stores/token';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';;
const instance = axios.create({baseURL})

//添加请求拦截器
 instance.interceptors.request.use(
    (config)=>{
        //请求前回调
        const token = useTokenStore();
        if(token.token){
            config.headers.Authorization = token.token
        }
        return config
    },
    (err) => {
       //如果请求错误做什么
        if(err.response.status===401){
            ElMessage.error('请先登录！')
            router.push('/login')
        }else{
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
 )

//添加响应拦截器
instance.interceptors.response.use(
    result => {
        //如果业务状态码为0，代表本次操作成功
        if (result.data.code == 0) {
            return result.data;
        }                    
        //代码走到这里，代表业务状态码不是0，本次操作失败
        ElMessage.error(result.data.message || '服务异常')
        return Promise.reject(result.data);//异步的状态转化成失败的状态
    },
    err => {
        if(err.response.status===401){
            ElMessage.error('请先登录！')
            router.push('/login')
        }else{
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;