<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main')">< Back</div>
            <div class="title">
                <span>好友请求</span>
            </div>
        </div>
        <div class="infoRow" v-for="request in requestList">
            <span>{{request.name}}</span>
            <span class="ignore" @click="ignore(request.id)">忽略</span>
            <span class="accept" @click="accept(request.id)">同意</span>
        </div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        name: "FriendRequest",
        data() {
            return {
                requestList: null
            }
        },
        mounted() {
            this.axios.get("/friend/friendRequest").then(response => {
                this.requestList = response.data;
            }).catch(function (error) {
                alert(error.response.data);
            });
        },
        methods: {
            accept(id) {
                let req = {
                    "sendUserId": id,
                    "operationType": 1
                }
                this.axios.post("/friend/accept", req).then(() => {
                    alert("好友添加成功");
                    // 获取好友
                    this.axios.get("/user/friend").then(response => {
                        this.$store.commit('setFriendList', response.data);
                    }).catch((error) => {
                        if (error.response.status === 401) {
                            router.replace("/welcome")
                        }
                    });
                    this.$router.go(0);
                }).catch(function (error) {
                    alert(error.response.data.message);
                });
            },
            ignore(id) {
                let req = {
                    "sendUserId": id,
                    "operationType": 0
                }
                this.axios.post("/friend/accept", req).then(() => {
                    alert("已忽略该好友请求");
                    this.$router.go(0);
                }).catch(function (error) {
                    alert(error.response.data.message);
                });
            },

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
        text-align: right;
    }

    .title {
        height: 100%;
        padding-right: 20px;
        font-size: 24px;
        font-weight: bolder;
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
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
        text-align: center;
    }

    .action {
        float: right;
    }

    .ignore {
        color: red;
        cursor: pointer;
        float: right;
        margin: 0 10px
    }

    .accept {
        color: green;
        cursor: pointer;
        float: right;
    }
</style>