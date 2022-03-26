package real.skill;
//share by chibikun
public class NClass {

    public int classId;

    public String name;

    public SkillTemplate[] skillTemplates;
    
    public SkillTemplate getSkillTemplate(int tempId){
        for (SkillTemplate skillTemplate : skillTemplates) {
            if (skillTemplate.id == tempId){
                return skillTemplate;
            }
        }
        return null;
    }
}
