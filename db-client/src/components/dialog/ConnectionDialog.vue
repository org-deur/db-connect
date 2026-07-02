<script setup lang="ts">
import { reactive, ref, watch } from "vue";
import { ConnectionRequest } from "../../types/connection/ConnectionRequest";
import { createConnection, testConnection } from "../../api/connection";
import { DatabaseType } from "../../types/DatabaseType";

const emit = defineEmits<{
    (e: "close"): void;
    (e: "created"): void;
}>();

const form = reactive<ConnectionRequest>({
    type: "POSTGRES",
    host: "localhost",
    port: 5432,
    database: "postgres",
    username: "postgres",
    password: ""
});

const testing = ref(false);

const connecting = ref(false);

const tested = ref(false);

const message = ref("");

watch(form, () => {

    tested.value = false;

    message.value = "";

}, { deep: true });

async function onTest() {

    testing.value = true;

    message.value = "";

    try {

        const res = await testConnection(form);

        tested.value = res.success;

        message.value = res.message;

    } catch (e: any) {

        tested.value = false;

        message.value = e.message;

    } finally {

        testing.value = false;

    }

}

async function onConnect() {

    connecting.value = true;

    try {

        await createConnection(form);

        emit("created");

        emit("close");

    } catch (e:any) {

        message.value = e.message;

    } finally {

        connecting.value = false;

    }

}

function changeType(type: DatabaseType){

    form.type=type;

    switch(type){

        case "POSTGRES":

            form.port=5432;

            break;

        case "MYSQL":

            form.port=3306;

            break;

        case "ORACLE":

            form.port=1521;

            break;

        case "SQLSERVER":

            form.port=1433;

            break;

        case "SQLITE":

            form.port=0;

            break;

    }

}

</script>

<template>

    <div class="overlay">

        <div class="dialog">

            <h2>New Connection </h2>

            <label>
            Database
                <select
                    :value="form.type"
                    @change="changeType(($event.target as HTMLSelectElement).value as DatabaseType)"
                >
                    <option>POSTGRES</option>
                    <option>MYSQL</option>
                    <option>ORACLE</option>
                    <option>SQLSERVER</option>
                    <option>SQLITE</option>

                </select>
            </label>

            <label>
            Host
                <input v-model="form.host"/>
            </label>

            <label>
            Port
                <input
                type="number"
                v-model="form.port"
                />
            </label>

            <label>
            Database
                <input
                v-model="form.database"
                />
            </label>

            <label>
            Username
                <input
                v-model="form.username"
                />
            </label>

            <label>
            Password
                <input
                type="password"
                v-model="form.password"
                />
            </label>

            <p>{{ message }} </p>
            <div class="buttons">

                <button
                    @click="$emit('close')">
                    Cancel
                </button>

                <button
                @click="onTest"
                :disabled="testing"
                >
                Test
                </button>

                <button
                @click="onConnect"
                :disabled="!tested || connecting"
                >
                Connect
                </button>

            </div>
        </div>
    </div>

</template>

<style scoped>

.overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, .4);

    display: flex;
    justify-content: center;
    align-items: center;

    padding: 20px;
    box-sizing: border-box;
}

.dialog {
    width: min(450px, 100%);
    background: white;
    border-radius: 12px;
    padding: 24px;

    display: flex;
    flex-direction: column;
    gap: 14px;

    box-sizing: border-box;

    max-height: 90vh;
    overflow-y: auto;
}

label {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

input,
select {
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
}

.buttons {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    flex-wrap: wrap;
}

</style>
