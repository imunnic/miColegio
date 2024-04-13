import axios from 'axios';

const host='http://localhost:8080/api';
const reservasEndPoint='/reservas';

export default class reservasService{
    
    getAll(){
        return axios.get(host + reservasEndPoint)
    }

    create(reserva) {
        return axios.post(host + reservasEndPoint, reserva);
    }

    delete(href){
        return axios.delete(href)
    }

    update(id, lugarData){
        return axios.put(host + reservasEndPoint + "/"+ id, lugarData)
    }
}