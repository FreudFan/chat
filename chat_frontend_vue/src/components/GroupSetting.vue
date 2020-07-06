<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main/groupchat')">< Back</div>
            <div class="title">
                <span>群设置</span>
            </div>
        </div>
        <div class="groupInfo">
            <div class="infoRow">群主：{{masterName}}</div>
            <div class="infoRow">群名：<input type="text" size="15" :placeholder="this.groupSetting.name"
                                           v-model="groupSetting.name" :disabled="currentRoleId===0"></div>
            <div class="infoRow" style="border: none">群介绍</div>
            <div class="infoRow" style="margin-top: 5px;height: 100px">
                <textarea :placeholder="groupSetting.info" v-model="groupSetting.info"
                          :disabled="currentRoleId===0"></textarea>
            </div>
            <div class="infoRow save" @click="saveInfo" v-text="saveButton"></div>
            <div class="infoRow disband" @click="disbandGroup" v-text="disbandButton"></div>
        </div>
        <div class="groupMember">
            <div class="infoRow" style="margin: 50px 0"><h2>群成员</h2></div>
            <div class="memberBox">
                <div class="member" v-for="member in memberList" v-if='memberList[0]'
                     @mouseenter="showAction(member.id)" @mouseleave="show = null">
                    <span class="chara">[{{member.groupId === 2 ? '群主' : member.groupId === 1 ? '管理员' : '群员' }}] </span>
                    <span class="name">{{member.name}}</span>
                    <div class="actionBox" v-show="member.id === show">
                        <span class="setAdmin" v-show="currentRoleId === 2 && member.groupId === 0"
                              @click="setAdmin(member.id)">设为管理员</span>
                        <span class="removeAdmin" v-show="currentRoleId === 2 && member.groupId === 1"
                              @click="removeAdmin(member.id)">取消管理员</span>
                        <span class="removeMember" v-show="currentRoleId > member.groupId"
                              @click="removeMember(member.id)">移出群聊</span>
                    </div>
                </div>
            </div>
            <div class="joinRequest" v-if="currentRoleId > 0">
                <div class="infoRow" style="margin: 50px 0"><h2>加群请求</h2></div>
                <div class="member" v-for="request in joinRequest" @mouseenter="showAction(request.id)"
                     @mouseleave="show = null">
                    <span>{{request.userVo.name}}</span>
                    <div class="actionBox" v-show="request.id === show">
                        <span class="accept" @click="joinAccept(request.sendUserId)">允许加群</span>
                        <span class="ignore" @click="joinIgnore(request.sendUserId)">忽略</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import router from "../router";

    export default {
        name: "GroupSetting",
        data() {
            return {
                show: null,
                currentRoleId: null,
                saveButton: '保存',
                disbandButton: '解散该群',
                masterName: null,
                groupSetting: this.$store.state.queryResult,
                memberList: {
                    "id": null,
                    "name": null,
                    "nickname": null,
                    "gender": null,
                    "groupId": 0,
                },
                joinRequest: null
            }
        },
        mounted() {
            // 检查群权限
            this.axios.get("/group/queryUserRole/" + this.groupSetting.id).then(response => {
                this.currentRoleId = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
            // 搜索群主用户名
            this.axios.get("/user/queryUser/" + this.groupSetting.masterId).then(response => {
                this.masterName = response.data.name;
            }).catch(function (error) {
                console.log(error.response.data);
            });
            // 拉取群成员
            this.axios.get("/group/members/" + this.groupSetting.id).then(response => {
                this.memberList = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
            // 拉取加群请求
            this.axios.get("/group/searchGroupRequest/" + this.groupSetting.id).then(response => {
                console.log(response.data)
                this.joinRequest = response.data;
            }).catch(function (error) {
                console.log(error.response.error);
            });
        },
        methods: {
            showAction(member) {
                this.show = member;
            },
            saveInfo() {
                delete this.groupSetting.flag;
                delete this.groupSetting.createTime;
                delete this.groupSetting.recMsg;
                this.axios.post('/group/changeGroupInfo', this.groupSetting).then(() => {
                    this.saveButton = "保存成功！";
                    setTimeout(() => {
                        this.axios.get("/user/group").then(response => {
                            this.$store.commit('setGroupList', response.data);
                        })
                        router.replace("/main/groupchat");
                    }, 1000)
                }).catch(error => {
                    alert(error.response.data.message);
                });
            },
            setAdmin(userId) {
                this.axios.post('/group/setUserManageRole/' + this.groupSetting.id + '/' + userId).then(() => {
                    this.$router.go(0);
                }).catch(error => {
                    console.log(error.response.data)
                });
            },
            removeAdmin(userId) {
                this.axios.post('/group/removeUserManageRole/' + this.groupSetting.id + '/' + userId).then(() => {
                    this.$router.go(0);
                }).catch(error => {
                    console.log(error.response.data)
                });
            },
            removeMember(userId) {
                this.axios.post('/group//removeGroupUser/' + this.groupSetting.id + '/' + userId).then(() => {
                    this.$router.go(0);
                }).catch(error => {
                    console.log(error.response.data)
                });
            },
            disbandGroup() {
                this.axios.post('/group/dissolute/' + this.groupSetting.id).then(() => {
                    this.disbandButton = '解散成功';
                    // 获取群组
                    this.axios.get("/user/group").then(response => {
                        this.$store.commit('setGroupList', response.data);
                    }).catch((error) => {
                        if (error.response.status === 401) {
                            router.replace("/welcome")
                        }
                    })
                    setTimeout(function () {
                        router.push('/main');
                    }, 1000)
                }).catch(error => {
                    console.log(error.response.data)
                });

            },
            joinAccept(userId) {
                let req = {
                    sendGroupId: null,
                    sendUserId: null,
                    operationType: 1,
                };
                req.sendGroupId = this.groupSetting.id;
                req.sendUserId = userId;
                this.axios.post('/group/acceptGroup', req).then(() => {
                    this.$router.go(0);
                }).catch(error => {
                    console.log(error.response.data)
                });
            },
            joinIgnore(userId) {
                let req = {
                    sendGroupId: null,
                    sendUserId: null,
                    operationType: 0,
                };
                req.sendGroupId = this.groupSetting.id;
                req.sendUserId = userId;
                this.axios.post('/group/acceptGroup', req).then(() => {
                    this.$router.go(0);
                }).catch(error => {
                    console.log(error.response.data)
                });
            },
        },
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
        text-align: center;
        height: 50px;
        margin: 0 200px;
        font-size: 16px;
        line-height: 70px;
        border-bottom: 1px solid #CCCCCC;
    }

    .groupInfo textarea {
        width: 230px;
        height: 85px;
        margin-bottom: 10px;
        border-bottom: 1px solid #CCCCCC;
    }

    .save {
        font-weight: bold;
        cursor: pointer;
    }

    .memberBox {
        width: 604px;
        margin: 0 auto;
        display: flex;
        flex-wrap: wrap;
    }

    .member {
        width: 300px;
        height: 50px;
        line-height: 40px;
        border: 1px solid #CCCCCC;
        box-sizing: border-box;
        padding: 5px;
        margin: 1px;
    }

    .actionBox {
        float: right;
    }

    .setAdmin {
        color: green;
        cursor: pointer;
    }

    .removeAdmin {
        color: gray;
        cursor: pointer;
    }

    .removeMember {
        margin-left: 10px;
        color: red;
        cursor: pointer;
    }

    .disband {
        color: red;
        font-weight: bold;
        cursor: pointer;
    }

    .accept {
        color: green;
        cursor: pointer;
        margin-right: 10px;
    }

    .ignore {
        color: red;
        cursor: pointer;
    }
</style>