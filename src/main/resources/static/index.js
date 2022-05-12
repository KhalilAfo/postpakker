function regPakke() {
    const pakke = {
        fornavn : $("#fornavn").val(),
        etternavn : $("#etternavn").val(),
        adresse : $("#adresse").val(),
        postnr : $("#postnr").val(),
        telefonnr : $("#telefonnr").val(),
        epost : $("#epost").val(),
        volum : $("#volum").val(),
        vekt : $("#vekt").val()
    };
    $.post("/lagre", pakke, function(){
    });
}
