<template>
    <div class="content">
        <form action="" id="registerForm">
            <h1>Register</h1>
            <label>
                <input type="text" placeholder="Nickname" class="input" v-model="params.name">
            </label>
            <label>
                <input type="password" placeholder="Password" class="input" v-model="params.password">
            </label>
            <div class="genderBox">
                <label>
                    <input type="radio" name="gender" value="1" v-model="params.gender">
                </label><span class="gender">Male</span>
                <span style="display: inline-block;width: 20px"></span>
                <label>
                    <input type="radio" name="gender" value="2" v-model="params.gender">
                </label><span class="gender">Female</span>
            </div>
            <label>
                <input type="text" placeholder="Email" class="input" v-model="params.email">
            </label>
            <label>
                <input type="text" placeholder="Telephone" class="input" v-model="params.telephone"
                       @keypress.enter="register">
            </label>
            <br><br>
            <router-link to="/" id="back">← Back to login</router-link>
            <span style="display: inline-block;width: 100px"></span>
            <a id="submit" @click="register" style="cursor: pointer">Submit →</a>
        </form>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        data: function () {
            return {
                params: {
                    name: "",
                    password: "",
                    gender: "",
                    email: "",
                    telephone: ""
                }
            }
        },
        methods: {
            register() {
                this.axios.post("/auth/register", this.params).then(function (response) {
                    console.log(response.data);
                    alert("注册成功")
                    router.push("/");
                }).catch(function (error) {
                    alert(error.response.data.message);
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

    .content {
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

    #registerForm {
        text-align: center;
    }

    .input {
        width: 280px;
        height: 30px;
        text-align: center;
        border-radius: 6px;
        margin: 5px 0;
        border: none;
        border-bottom: 1px solid #eee;
        outline: none;
    }

    .gender, #back, #submit {
        color: #eee;
        font-size: 10px;
        text-decoration: none;
    }
</style>