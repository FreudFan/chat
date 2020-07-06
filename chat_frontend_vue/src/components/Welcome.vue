<template>
    <div id="app">
        <form action="" id="loginForm">
            <h1 v-text="title"></h1>
            <label>
                <input type="text" placeholder="Email or Telephone" class="input" v-model="params.text" checked>
            </label>
            <br>
            <label>
                <input type="password" placeholder="Password" class="input" @keypress.enter="auth"
                       v-model="params.password">
            </label>
            <br><br>
            <span v-text="errMsg" id="errMsg"></span>
        </form>
        <h1>or</h1>
        <transition name="scale">
            <router-link to="/register" id="register">Register→</router-link>
        </transition>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        router,
        data() {
            return {
                title: "Login",
                params: {
                    text: "",
                    password: "",
                },
                errMsg: "请输入用户名和密码"
            }
        },
        mounted() {

        },
        methods: {
            auth() {
                this.axios.post("/auth/login", this.params).then(response => {
                    this.title = "Hello! " + response.data.name;
                    this.errMsg = "登陆成功"
                    this.$store.commit("setCurrentUser", response.data);
                    setTimeout(function () {
                        router.replace("/main");
                    }, 1000)
                }).catch(error => {
                    this.errMsg = error.response.data.message;
                })
            }
        }
    }
</script>

<style scoped>
    h1 {
        color: #eee;
        text-align: center;
    }

    #app {
        box-shadow: 0 0 10px #333333;
        width: 300px;
        height: 300px;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -150px;
        margin-top: -200px;
        border-radius: 10px;
        padding: 80px 10px;
        text-align: center;
    }

    #loginForm {
        text-align: center;
    }

    .input {
        width: 280px;
        height: 30px;
        text-align: center;
        border-radius: 6px;
        margin-top: 10px;
        border: none;
        border-bottom: 1px solid #eee;
        outline: none;
    }

    #register, #errMsg {
        color: #eee;
        font-size: 10px;
    }
</style>