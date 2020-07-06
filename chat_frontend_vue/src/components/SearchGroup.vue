<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main')">< Back</div>
            <div class="title">搜索群组</div>
        </div>
        <div class="result" v-for="item in $store.state.searchResult">
            <div class="resultRow">
                <div class="groupName">{{item.name}}</div>
                <div class="groupJoined">
                    <span v-show="item.joined">已加入</span>
                    <div class="joinButton" v-show="!item.joined" @click="joinRequest(item.groupId)">申请入群</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "searchResult",
        methods: {
            joinRequest(id) {
                this.axios.post("/group/requestGroup/" + id).then(response => {
                    console.log(response.data)
                    alert("加群申请已发送");
                }).catch(function (error) {
                    alert(error.response.data);
                });
            }
        }
    }
</script>

<style scoped>
    .content {
        padding: 10px;
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

    .result {
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
    }

    .resultRow {
        display: flex;
    }

    .groupName {
        width: 70%;
    }

    .groupJoined {
        width: 30%;
        text-align: center;
    }

    .joinButton {
        cursor: pointer;
    }
</style>