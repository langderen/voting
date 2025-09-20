import { defineStore } from 'pinia'

export const userStore = defineStore('userStore',{
  actions:{
    ChangeUserName(value:string){
      if(value&&value.length<10){
        this.username+=value
      }
    }
  },
  getters:{
    getUserName():string{
      return this.username.toUpperCase()
    }
  },
  state(){
    return {
      username:"wy666", //用户名
      isFinited:true //是否登录

    }
  }

})
