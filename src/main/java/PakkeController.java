public class PakkeController {
        @Autowired
        private JdbcTemplate db;
        private Logger logger = LoggerFactory.getLogger(PakkeController.class);
        @PostMapping("/lagre")
        @Transactional
        public void lagreMelding(Pakke p) {
            String regex1 = "[a-zæøåA-ZÆØÅ .\\-]{2,50}";
            String regex2= "[0-9]{4}";
            boolean fornavnOK = p.getFornavn().matches(regex1);
            boolean etternavnOK = p.getEtternavn().matches(regex1);
            boolean postnrOK = p.getPostnr().matches(regex2);
            if(fornavnOK & etternavnOK && postnrOK) {
                String sql1 = "INSERT INTO kunde (Fornavn,Etternavn,Adresse,Postnr,Telefonnr,Epost)" +
                        " VALUES(?,?,?,?,?,?)";
                String sql2 = "INSERT INTO pakke (KID,Volum,Vekt) VALUES(?,?,?)";
                KeyHolder id = new GeneratedKeyHolder();
                try {
                    db.update(con -> {
                        PreparedStatement par = con.prepareStatement(sql1, new String[]{"KId"});
                        par.setString(1, p.getFornavn());
                        par.setString(2, p.getEtternavn());
                        par.setString(3, p.getAdresse());
                        par.setString(4, p.getPostnr());
                        par.setString(5, p.getTelefonnr());
                        par.setString(6, p.getEpost());
                        return par;
                    }, id);
                    int kid = id.getKey().intValue();
                    db.update(sql2, kid, p.getVolum(), p.getVekt());
                } catch (Exception e) {
                    logger.error("Feil i lagre pakke! " + e);
                }
            }
            else{
                logger.error("Feil i input validering! ");
            }
        }
}
