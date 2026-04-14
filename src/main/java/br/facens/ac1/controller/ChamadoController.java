
package br.facens.ac1.controller;

import br.facens.ac1.model.Chamado;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {
    private static int proximoId = 1;
    private static final List <Chamado> chamados = new ArrayList<>();
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody Chamado c){
       
        c.setStatus("ABERTO");
        c.setId(proximoId);
        
        proximoId++;
        chamados.add(c);
        
        return ResponseEntity.ok(c);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Chamado>> listar() {
        return ResponseEntity.ok(chamados);
    }
    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable int id){
        for(Chamado c : chamados){
            if(c.getId()==id){
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(404).body("Reserva não encontrada!");
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable int id, @RequestBody Chamado chamadoAtualizado){
        for (Chamado c : chamados){
            if (c.getId()==id){
                c.setSolicitante(chamadoAtualizado.getSolicitante());
                c.setEquipamento(chamadoAtualizado.getEquipamento());
                c.setDescricao(chamadoAtualizado.getDescricao());
                c.setDataAbertura(chamadoAtualizado.getDataAbertura());
                c.setPrioridade(chamadoAtualizado.getPrioridade());
                c.setStatus(chamadoAtualizado.getStatus());

                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(404).body("Reserva não encontrada!");
    }
    
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Object> deletar(@PathVariable int id){
        for (Chamado c : chamados){
            if(c.getId()==id){
                chamados.remove(c);
                return ResponseEntity.ok().body("Chamado removido com sucesso!");
            }
            }
        return ResponseEntity.status(404).body("Chamado não encontrada para remoção!");
    }
    
    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Object> cancelar(@PathVariable int id){
        for (Chamado c : chamados){
            if(c.getId()==id){
                c.setStatus("FECHADO");
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(404).body("Chamado não encontrada para alteração!");
    }
    
    @GetMapping("/abertos")
    public ResponseEntity<List<Chamado>> listarAbertos(){
        List <Chamado> abertos = new ArrayList<>();
        for(Chamado c : chamados){
            if(c.getStatus().equals("ABERTO")){
                abertos.add(c);

            }
        }
        return ResponseEntity.ok(abertos);
    }
    
    @GetMapping("/solicitantes/{solicitante}")
    public ResponseEntity<Object> mostrarSolicitante(@PathVariable String solicitante){
        Stream<Chamado> encontrada = chamados.stream().filter(s -> s.getSolicitante().equalsIgnoreCase((solicitante)));
        return ResponseEntity.ok(encontrada);
    }
    
    @GetMapping("/equipamento/{equipamento}")
    public ResponseEntity<Object> mostrarEquipamento(@PathVariable String equipamento){
        Stream<Chamado> encontrada = chamados.stream().filter(s -> s.getEquipamento().equalsIgnoreCase((equipamento)));
        return ResponseEntity.ok(encontrada);
    }
    
    @GetMapping("/prioridade")
    public ResponseEntity<List<Chamado>> listarPrioriodade(){
        List <Chamado> abertos = new ArrayList<>();
        for(Chamado c : chamados){
            if(c.getStatus().equals("ABERTO")){
               
                abertos.add(c);
            }
            
        }
        return ResponseEntity.ok(abertos);
    }
}
