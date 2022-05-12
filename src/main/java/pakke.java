public class Pakke {
    private String fornavn;
    private String etternavn;
    private String adresse;
    private String postnr;
    private String telefonnr;
    private String epost;
    private String volum;
    private String vekt;
    // … (konstruktør som setter alle feltene ovenfor, no-arg konstruktør og get-/set-metoder)
    @RestController
    public class PakkeController {
        @Autowired
        private JdbcTemplate db;
        private Logger logger = LoggerFactory.getLogger(PakkeController.class);
        @PostMapping("/lagre")
        @Transactional
        public void lagreMelding(Pakke p) {
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
