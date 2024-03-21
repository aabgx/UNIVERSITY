using Lab8_CSharp.model;

namespace Lab8_CSharp.repository;

class JucatoriActiviFileRepository:InFileRepository<string,JucatorActiv>
{
    public JucatoriActiviFileRepository(string fileName) : base(fileName, EntityToFileMapping.createJucatorActiv)
    {
        
    }
}