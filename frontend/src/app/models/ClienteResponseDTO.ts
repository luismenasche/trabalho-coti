import { ClienteDTO } from './basic/ClienteDTO';
import { EnderecoComIdDTO } from './basic/EnderecoComIdDTO';

export interface ClienteResponseDTO extends ClienteDTO {
  id: string;
  enderecos: EnderecoComIdDTO[];
}
