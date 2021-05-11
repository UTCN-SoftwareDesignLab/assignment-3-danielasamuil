<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{"Create Consultation"}}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="consultation.scheduled" label="Date ('yyyy-mm-dd')" />
          <v-text-field v-model="consultation.details" label="Details" />
          <v-text-field v-model="consultation.patientId" label="Patient's ID" />
          <v-text-field v-model="consultation.doctorId" label="Doctor's ID" />
        </v-form>
        <v-card-actions>
          <v-btn @click="createConsultation">
            {{"Create"}}
          </v-btn>
          <v-btn @click="deleteConsultation">Delete Consultation</v-btn>
          <v-btn @click="patientArrived">Patient Arrived</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "ConsultationDialog",
  props: {
    consultation: Object,
    opened: Boolean,
  },
  data() {
    return {
      connected: false,
    }
  },
  methods: {
    createConsultation() {
        api.consultations.create({
          scheduled: this.consultation.scheduled,
          details: this.consultation.details,
          patientId: this.consultation.patientId,
          doctorId: this.consultation.doctorId,
        })
            .then(() => this.$emit("refresh"));
    },

    deleteConsultation(){
      api.consultations.delete({
        id: this.consultation.id,
      })
          .then(() => this.$emit("refresh"));
    },

    patientArrived(){
      api.webSocket.hello(this.consultation.date, this.consultation.patientId);
    },
  },
  computed: {
    isNew: function () {
      return !this.consultation.id;
    },
  },
  created() {
    this.connected = api.webSocket.connect();
  },
};
</script>

<style scoped>
</style>