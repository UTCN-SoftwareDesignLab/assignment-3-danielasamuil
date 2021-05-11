<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{"Create user"}}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username"/>
          <v-text-field v-model="user.email" label="Email"/>
          <v-text-field type="password" v-model="user.password" label="Password"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="createUser">
            {{"Create"}}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteUser">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    createUser() {
      api.users
          .create({
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
            roles: ["SECRETARY"],
          })
          .then(() => this.$emit("refresh"));
    },

    deleteUser() {
      api.users.delete(this.user.id)
          .then((response) => {
            if (response == true)
              this.$emit("refresh")
          });
    },
  },

  computed: {
    isNew: function () {
      return !this.user.id;
    }
  }

};
</script>

<style scoped></style>