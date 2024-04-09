<template>
  <div>
    <Qalendar class="calendario" :config="configuracion">
      <template #dayCell="{ dayData }">
        <div class="celdaDia">
          <div> {{ dayData.dateTimeString.substring(8, 10) }}</div>
          <div> {{ dayData.events.length }} clases</div>
        </div>
      </template>
    </Qalendar>
  </div>
</template>
<script>
import { Qalendar } from '../../node_modules/qalendar';
export default {
  components: { Qalendar },
  data() {
    return {
      configuracion: {
        dayBoundaries: {
          start: 9,
          end: 15
        },
        dayIntervals: {
          height: 150
        },
        style: {
          fontFamily: 'arial'
        }
      }
    }
  }
}
</script>
<style scoped>
.calendario {
  width: 50%;
  text-align: start;
}
.celdaDia{
  min-height: 60px;
}
</style>