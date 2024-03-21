using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

namespace Lab8_CSharp.repository;

class JucatoriActiviFileRepository:InFileRepository<string,JucatorActiv>
{
    public JucatoriActiviFileRepository(IValidator<JucatorActiv> validator,string fileName) : base(validator,fileName, EntityToFileMapping.createJucatorActiv)
    {
        
    }
}