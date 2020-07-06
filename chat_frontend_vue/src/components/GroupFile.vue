<template>
    <div class="content">
        <div class="header">
            <div class="backButton" @click="$router.push('/main/groupchat')">< Back</div>
            <div class="title">
                <span>群文件</span>
            </div>
            <div class="fileList">
                <div class="infoRow">
                    <input class="file" name="file" type="file" @change="fileUpload">
                </div>
                <div class="infoRow" v-for="file in fileList">
                    {{file.fileName}}({{file.contentSize}}KB)
                    <span @click="fileDownload(file.fileId)" style="cursor: pointer;color: green"> 下载</span>
                    <span @click="fileDelete(file.fileId)" style="cursor: pointer;color: red" v-if="currentRoleId > 0"> 删除</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "GroupFile",
        data() {
            return {
                currentRoleId: 0,
                fileList: []
            }
        },
        mounted() {
            // 检查群权限
            this.axios.get("/group/queryUserRole/" + this.$store.state.queryResult.id).then(response => {
                this.currentRoleId = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
            // 拉取群文件
            this.axios.get("/group/showGroupAttachmentList/" + this.$store.state.queryResult.id).then(response => {
                console.log(response.data)
                this.fileList = response.data;
            }).catch(function (error) {
                console.log(error.response.data);
            });
        },
        methods: {
            fileUpload(e) {
                let file = e.target.files[0];
                let param = new FormData();
                param.append('file', file);
                console.log(param.get('file'));
                let config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                };
                this.axios.post('/group/uploadGroupAttachment/' + this.$store.state.queryResult.id, param, config)
                    .then(response => {
                        console.log(response.data);
                        alert('文件上传成功');
                        this.$router.go(0);
                    }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            fileDownload(e) {
                this.axios.get('/file/down?id=' + e).then(() => {
                    window.open('http://localhost:8080/file/down?id=' + e);
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            },
            fileDelete(e) {
                this.axios.post('/group/deleteGroupAttachment/' + this.$store.state.queryResult.id + '/' + e).then(response => {
                    console.log(response.data);
                    alert('删除成功');
                    this.$router.go(0)
                }).catch(function (error) {
                    console.log(error.response.data);
                });
            }
        }
    }
</script>

<style scoped>
    .content {
        padding: 25px;
        height: 100%;
        box-sizing: border-box;
        overflow: hidden;
    }

    .header {
        position: absolute;
        width: 650px;
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

</style>