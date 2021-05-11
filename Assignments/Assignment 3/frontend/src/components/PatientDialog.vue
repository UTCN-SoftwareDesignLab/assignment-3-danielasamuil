<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Create patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name"/>
          <v-text-field v-model="patient.address" label="Address"/>
          <v-text-field v-model="patient.PNC" label="Personal Numerical Code"/>
          <v-text-field v-model="patient.identityCardNr" label="Card Number"/>
          <v-text-field v-model="patient.birthDate" label="Birth Date ('yyy-mm-dd')"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="createPatient">
            {{"Create"}}
          </v-btn>
          <v-btn @click="deletePatient">Delete Patient</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    createUser() {
      api.patients
          .create({
            name: this.patient.name,
            address: this.patient.address,
            identityCardNr: this.patient.identityCardNr,
            PNC: this.patient.PNC,
            birthDate: this.patient.birthDate,
          })
          .then(() => this.$emit("refresh"));
    },

    deletePatient() {
      api.patients.delete({
        id: this.patient.id,
      })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
}
</script>

<style scoped>
</style>