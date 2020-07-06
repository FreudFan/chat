<template>
    <div class="editInfo">
        <div class="header">
            <div class="backButton" @click="$router.push('/main')">< Back</div>
            <div class="title">修改用户信息</div>
        </div>
        <div class="infoRow">名称：<input type="text" v-model="info.name"></div>
        <div class="infoRow">性别：
            <input type="radio" name="gender" value="1" v-model="info.gender">
            <span class="gender">男</span>&nbsp;&nbsp;&nbsp;
            <input type="radio" name="gender" value="2" v-model="info.gender">
            <span class="gender">女</span>
        </div>
        <div class="infoRow">生日：<input type="text" v-model="info.birthday"></div>
        <div class="infoRow">邮箱：<input type="text" v-model="info.email"></div>
        <div class="infoRow">电话：<input type="text" v-model="info.telephone"></div>
        <div class="infoRow save" @click="saveInfo" v-text="saveButton"></div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        name: "EditInfo",
        data() {
            return {
                info: null,
                saveButton: "保存",
                errMsg: null
            }
        },
        created() {
            this.info = this.$store.state.currentUser;
        },
        methods: {
            saveInfo() {
                this.axios.post('/user/info', this.info).then(response => {
                    this.$store.commit("setCurrentUser", response.data);
                    this.saveButton = "保存成功！";
                    setTimeout(function () {
                        router.push("/main");
                    }, 1000)
                }).catch(error => {
                    alert(error.response.data.message);
                });
            }
        },
    }
</script>

<style scoped>
    .editInfo {
        padding: 25px;
    }

    .header {
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #CCCCCC;
        text-align: right;
    }

    .title {
        height: 100%;
        font-size: 24px;
        font-weight: bolder;
        text-align: center;
        line-height: 70px;
        cursor: pointer;
    }

    .backButton {
        position: absolute;
        cursor: pointer;
        font-size: 20px;
        color: #888888;
    }

    .infoRow {
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
        text-align: center;
    }

    .save {
        font-weight: bold;
        cursor: pointer;
    }

</style>