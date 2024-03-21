using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

namespace Lab8_CSharp.repository;

class EleviFileRepository:InFileRepository<string,Elev>
{
    public EleviFileRepository(IValidator<Elev> validator,string fileName) : base(validator,fileName, EntityToFileMapping.createElev)
    {
        
    }
}