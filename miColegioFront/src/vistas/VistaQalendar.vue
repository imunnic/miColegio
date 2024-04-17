<template>
    <div>
        <Qalendar :events="eventos" :config="configuracion" :key="dispo"></Qalendar>
        <v-btn @click="add"></v-btn>
    </div>
</template>
<script>
import { Qalendar } from 'qalendar';
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
                    displayClickableInterval: true,
                    height: 90
                },
                style: {
                    fontFamily: 'arial'
                }
            },
                eventos: [
                    {
                        title: "Meeting with Dora",
                        with: "Albert Einstein",
                        time: { start: "2024-04-15 09:52", end: "2024-04-15 10:37" },
                        color: "green",
                        isEditable: true,
                        id: "de471c78cb5c",
                        description:
                            "Think of me as Yoda. Only instead of being little and green, I wear suites and I'm awesome.",
                    }],
                dispo:true
            }
        },
            methods: {
            add(){
                let evento = {
                    title: "Meeting with Dora",
                    with: "Albert Einstein",
                    time: { start: "2024-04-15 13:52", end: "2024-04-15 14:37" },
                    color: "green",
                    isEditable: true,
                    id: "de471c78cb5z",
                    description:
                        "Think of me as Yoda. Only instead of being little and green, I wear suites and I'm awesome.",
                };
                this.eventos.push(evento)
                console.log(this.eventos);
                this.dispo=!this.dispo;
            }
        }
    }
</script>