package org.gov.moussaada.subventions_service.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class KafakaPaysanService {

    private ReclamationTraite lastMessage;

//    @Autowired
//    private ReclamationDAO reclamationDAO;


    @KafkaListener(topics = "admin-topic", groupId = "paysan-group")
    public void UpdateStatusReclamation(int id){
//        Reclamation reclamation = reclamationDAO.findById(id).get();
//        reclamation.setInTreatment(true);
//        reclamation.setId_reclamation(id);
//        reclamationDAO.save(reclamation);
        log.info("voila le nv reclamation : {}");
    }

}
