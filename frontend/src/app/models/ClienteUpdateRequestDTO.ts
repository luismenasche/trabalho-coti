import { ClienteDTO } from './basic/ClienteDTO';
import { EnderecoComIdDTO } from './basic/EnderecoComIdDTO';

export interface ClienteUpdateRequestDTO extends ClienteDTO {
  id: string;
  endereco: EnderecoComIdDTO;
}
