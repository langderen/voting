import { defineStore } from 'pinia'

export const userStore = defineStore('userStore',{
  actions:{
    ChangeUserIde(value:string){
      if(value&&value.length<10){
        this.userId+=value
      }
    }
  },
  getters:{
    getUserName():string{
      return this.userId.toUpperCase()
    }
  },
  state(){
    return {
      userId:"", //用户名
      isFinited:false, //是否登录
      AvatarUrl:"", //头像地址
      userName:"", //昵称
      userEmail:"", //邮箱
    }
  }
})
