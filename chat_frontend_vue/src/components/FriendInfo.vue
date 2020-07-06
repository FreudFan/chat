<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.back()">< Back</div>
            <div class="title">好友信息</div>
        </div>
        <div class="infoRow">用户名：{{$store.state.queryResult.name}}</div>
        <div class="infoRow">备注：{{$store.state.queryResult.nickname}}</div>
        <div class="infoRow">性别：{{$store.state.queryResult.gender===1?'男':'女'}}</div>
        <div class="infoRow deleteFriend" @click="deleteFriend($store.state.queryResult.id)">删除好友</div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        name: "FriendInfo",
        methods: {
            deleteFriend(id) {
                this.axios.post("/friend/delete/" + id).then(response => {
                    console.log(response.data);
                    alert("删除成功");
                    this.axios.get("/user/friend").then(response => {
                        this.$store.commit('setFriendList', response.data);
                    }).catch((error) => {
                        if (error.response.status === 401) {
                            router.replace("/welcome")
                        }
                    });
                    router.replace("/main");
                }).catch(error => {
                    alert(error.response.data.message);
                });
            }
        }
    }
</script>

<style scoped>
    .content {
        padding: 25px;
    }

    .header {
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #CCCCCC;
    }

    .title {
        height: 100%;
        font-size: 24px;
        font-weight: bolder;
        margin: 0 auto;
        text-align: center;
        line-height: 70px;
    }

    .backButton {
        position: absolute;
        cursor: pointer;
        font-size: 20px;
        color: #888888;
    }

    .infoRow {
        text-align: center;
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
        padding-left: 10px;
    }

    .deleteFriend {
        color: red;
        font-weight: bold;
        cursor: pointer;
    }
</style>