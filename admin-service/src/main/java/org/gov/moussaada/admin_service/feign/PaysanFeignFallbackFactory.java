//package org.gov.moussaada.admin_service.feign;
//
//import org.gov.moussaada.paysan_service.model.Reclamation;
//import org.springframework.cloud.openfeign.FallbackFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class PaysanFeignFallbackFactory implements FallbackFactory<PaysanFeign> {
//    @Override
//    public PaysanFeign create(Throwable cause) {
//        return new PaysanFeign() {
//            @Override
//            public List<Reclamation> getAll() {
//                return List.of();
//            }
//
//            @Override
//            public Reclamation geById(int id) {
//                return null;
//            }
//
//            @Override
//            public Reclamation updateReclamationById(int id) {
//                return null;
//            }
//        };
//    }
//}
//
