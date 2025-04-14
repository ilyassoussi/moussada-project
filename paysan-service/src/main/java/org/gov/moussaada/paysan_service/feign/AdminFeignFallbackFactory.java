//package org.gov.moussaada.paysan_service.feign;
//
//import org.gov.moussaada.admin_service.model.TraitmentReclamation;
//import org.springframework.cloud.openfeign.FallbackFactory;
//
//public class AdminFeignFallbackFactory implements FallbackFactory<AdminFeign> {
//    @Override
//    public AdminFeign create(Throwable cause) {
//        return new AdminFeign() {
//
//            @Override
//            public TraitmentReclamation getReponse(int id) {
//                return null;
//            }
//        };
//    }
//}
