import { ClienteDTO } from './basic/ClienteDTO';
import { EnderecoDTO } from './basic/EnderecoDTO';

export interface ClienteCreateRequestDTO extends ClienteDTO {
  endereco: EnderecoDTO;
}
